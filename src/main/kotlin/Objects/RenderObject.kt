package Objects

import Renderable
import Tickable
import Vertex
import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.sin

abstract class RenderObject(private val vertCount: Int, private val indexCount: Int, protected val color: Vector4): Renderable, Tickable {
    private val vertBuff: VertexBuffer = vertexBuffer(vertexFormat {
        position(3)
        normal(3)
        color(4)
    }, vertCount)
    private val indexBuff: IndexBuffer = indexBuffer(indexCount, IndexType.INT16)
    var shader: ShadeStyle = shadeStyle {
        vertexTransform = """
                    va_color = a_color;
                """.trimIndent()
        fragmentTransform = """
                        vec3 lightDir = normalize(vec3(0.3, 1.0, 0.5));
                        float l = dot(va_normal, lightDir) * 0.4 + 0.5;
                        x_fill.rgb *= l; 
                    """.trimIndent()
    }
    protected var position: Vector3 = Vector3.ZERO
    var vertices: Array<Vertex> = Array(vertCount) {Vertex(Vector3.ZERO, Vector3.ZERO, Vector4.ZERO)}
    private var rotation: Vector3 = Vector3.ZERO
    private var delta: Double = 0.0
    protected var scale: Double = 1.0

    fun insertVerts(){
        for(i in 0 until vertCount){
            vertBuff.put(i) {
                write(vertices[i].position)
                write(vertices[i].normal)
                write(vertices[i].color)
            }
        }
    }

    fun insertIndexes(indexArr: ShortArray){
        val bb = ByteBuffer.allocateDirect(indexCount*2)
        bb.order(ByteOrder.nativeOrder())

        for (i in 0 until indexCount){
            bb.putShort(indexArr[i])
        }

        bb.rewind()
        indexBuff.write(bb)
    }

    fun setObjectScale(scale: Double){
        this.scale = scale
    }

    fun hover(hoverHeight: Double, hoverSpeed: Double, intensity: Double, seconds: Double){
        position = Vector3(position.x, (sin(seconds * hoverSpeed)*intensity + intensity * 1.0 + hoverHeight), position.z)
    }

    fun spin(degrees: Double){
        rotation = Vector3(rotation.x, (rotation.y + degrees * delta), rotation.z)
    }

    fun move(targetPos: Vector3, speed: Double){
        if(targetPos.distanceTo(position) >= 0.5){
            position += targetPos * 0.01 * speed
        }
    }

    override fun Program.render(drawer: Drawer) {
        drawer.isolated {
            shadeStyle = shader
            translate(position)
            rotate(Vector3.UNIT_X, rotation.x)
            rotate(Vector3.UNIT_Y, rotation.y)
            rotate(Vector3.UNIT_Z, rotation.z)
            scale(scale)
            vertexBuffer(indexBuff, listOf(vertBuff), DrawPrimitive.TRIANGLES)
        }
    }

    override fun tick(delta: Double) {
        this.delta = delta
    }

    protected fun calculateNormals(){
        var normalVec: Vector3

        for(i in vertices.indices){
            val currVert = vertices[i]
            var nextVert: Vertex

            if(i == vertices.size-1){
                nextVert = vertices[0]
            } else {
                nextVert = vertices[i+1]
            }

            normalVec = Vector3(
                vertices[i].normal.x + ((currVert.position.y - nextVert.position.y) * (currVert.position.z + nextVert.position.z)),
                vertices[i].normal.y + ((currVert.position.z - nextVert.position.z) * (currVert.position.x + nextVert.position.x)),
                vertices[i].normal.z + ((currVert.position.x - nextVert.position.x) * (currVert.position.y + nextVert.position.y))
            )

            vertices[i].normal = normalVec.normalized
        }
    }

    protected fun fillColor(color: Vector4){
        for(vertex in vertices){
            vertex.color = color
        }
    }

}