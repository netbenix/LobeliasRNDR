package Objects

import Renderable
import Tickable
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
        color(4)
    }, vertCount)
    private val indexBuff: IndexBuffer = indexBuffer(indexCount, IndexType.INT16)
    var shader: ShadeStyle = shadeStyle {}
    protected var position: Vector3 = Vector3.ZERO
    protected var rotation: Vector3 = Vector3.ZERO
    private var delta: Double = 0.0
    protected var scale: Double = 1.0

    fun fillIndexBuff(byteBuff: ByteBuffer) {
        indexBuff.write(byteBuff)
    }

    fun insertVert(offset: Int, pos: Vector3, color: Vector4){
        vertBuff.put(offset) {
            write(pos)
            write(color)
        }
    }

    fun insertIndexes(indexArr: ShortArray){
        val bb = ByteBuffer.allocateDirect(indexCount*2)
        bb.order(ByteOrder.nativeOrder())

        for (i in 0 until indexCount){
            bb.putShort(indexArr[i])
        }

        bb.rewind()
        fillIndexBuff(bb)
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
}