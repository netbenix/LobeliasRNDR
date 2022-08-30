import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class TestPyramidObject(vertCount: Int = 5, indexCount: Int = 18) : RenderObject(vertCount, indexCount) {
    private fun initObject(){
        this.insertVert(0, Vector3(-1.0,0.0,1.0), Vector4(1.0,0.0,0.0,1.0))
        this.insertVert(1, Vector3(1.0,0.0,1.0), Vector4(0.0,0.0,1.0,1.0))
        this.insertVert(2, Vector3(1.0,0.0,-1.0), Vector4(0.0,1.0,0.0,1.0))
        this.insertVert(3, Vector3(-1.0,0.0,-1.0), Vector4(1.0,0.0,1.0,1.0))
        this.insertVert(4, Vector3(0.0, 2.0, 0.0), Vector4(0.5, 0.5, 0.5, 1.0))

        this.insertIndexes(
            shortArrayOf(
                0, 1, 3,
                2, 1, 3,
                3, 4, 2,
                2, 4, 1,
                1, 4, 0,
                0, 4, 3
            )
        )

        this.shader = shadeStyle {
            vertexTransform = """
                    va_color = a_color;
                """.trimIndent()
            fragmentTransform = """
                    x_fill = va_color;
                """
        }
    }

    constructor(vec3: Vector3): this(5, 18){
        initObject()
        this.position = vec3
    }


    override fun Program.render(drawer: Drawer) {
        drawer.isolated {
            shadeStyle = shader
            translate(position)
            rotate(Vector3.UNIT_Y, state)
            vertexBuffer(indexBuff, listOf(vertBuff), DrawPrimitive.TRIANGLES)
        }
    }

    override fun tick(delta: Double) {
        state += 90.0 * delta
    }

    private var state: Double = 0.0
}