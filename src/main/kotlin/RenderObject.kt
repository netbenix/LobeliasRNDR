import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.sin

abstract class RenderObject(private val vertCount: Int, private val indexCount: Int): IRenderable, ITickable {
    val vertBuff: VertexBuffer = vertexBuffer(vertexFormat {
        position(3)
        color(4)
    }, vertCount)
    val indexBuff: IndexBuffer = indexBuffer(indexCount, IndexType.INT16)
    var shader: ShadeStyle = shadeStyle {}
    var position: Vector3 = Vector3(0.0, 0.0, 0.0)
    var rotation: Vector3 = Vector3(0.0, 0.0, 0.0)
    var delta: Double = 0.0
    var scale: Double = 1.0

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

    fun hover(hoverHeight: Double, hoverSpeed: Double, seconds: Double){
        position = Vector3(position.x, (sin(seconds * hoverSpeed)/4 + 0.25 + hoverHeight), position.z)
    }

    fun spin(degrees: Double){
        rotation = Vector3(rotation.x, (rotation.y + degrees * delta), rotation.z)
    }

    override fun Program.render(drawer: Drawer) {
        drawer.isolated {
            shadeStyle = shader
            translate(position)
            rotate(Vector3.UNIT_X, rotation.x)
            rotate(Vector3.UNIT_Y, rotation.y*1.2)
            rotate(Vector3.UNIT_Z, rotation.z)
            vertexBuffer(indexBuff, listOf(vertBuff), DrawPrimitive.TRIANGLES)
        }
    }

    override fun tick(delta: Double) {
        this.delta = delta
    }
}