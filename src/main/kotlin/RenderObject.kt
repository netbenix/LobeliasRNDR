import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.nio.ByteBuffer

class RenderObject(val vertCount: Int, val indexCount: Int) {
    val vertBuff: VertexBuffer = vertexBuffer(vertexFormat {
        position(3)
        color(4)
    }, vertCount)
    val indexBuff: IndexBuffer = indexBuffer(indexCount, IndexType.INT16)

    var shader: ShadeStyle = shadeStyle {}
    var position: Vector3 = Vector3(0.0, 0.0, 0.0)


    fun fillIndexBuff(byteBuff: ByteBuffer) {
        indexBuff.write(byteBuff)
    }
    fun insertVert(offset: Int, pos: Vector3, color: Vector4){
        vertBuff.put(offset) {
            write(pos)
            write(color)
        }
    }

}