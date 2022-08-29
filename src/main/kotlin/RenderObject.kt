import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.nio.ByteBuffer
import java.nio.ByteOrder

open class RenderObject(private val vertCount: Int, private val indexCount: Int) {
    val vertBuff: VertexBuffer = vertexBuffer(vertexFormat {
        position(3)
        color(4)
    }, vertCount)
    val indexBuff: IndexBuffer = indexBuffer(indexCount, IndexType.INT16)
    var shader: ShadeStyle = shadeStyle {}
    var position: Vector3 = Vector3(0.0, 0.0, 0.0)
    var rotation: Vector3 = Vector3(0.0, 0.0, 0.0)


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

}