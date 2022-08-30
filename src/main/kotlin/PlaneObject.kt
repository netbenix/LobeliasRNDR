import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class PlaneObject(vertCount: Int = 4, indexCount: Int = 6):RenderObject(vertCount, indexCount) {
    private fun initObject(){
        val color = Vector4(1.0,1.0,1.0,1.0)

        this.insertVert(0, Vector3(-1.0, 0.0, -1.0), color)
        this.insertVert(1, Vector3(-1.0, 0.0, 1.0), color)
        this.insertVert(2, Vector3(1.0, 0.0, 1.0), color)
        this.insertVert(3, Vector3(1.0, 0.0, -1.0), color)

        this.insertIndexes(
            shortArrayOf(
                0, 1, 2,
                2, 3, 1
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

    constructor(vec3: Vector3): this(4, 6){
        initObject()
        this.position = vec3
    }

}