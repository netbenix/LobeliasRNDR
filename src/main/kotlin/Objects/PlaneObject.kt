package Objects

import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class PlaneObject(vertCount: Int = 4, indexCount: Int = 6, color: Vector4 = Vector4(1.0)): RenderObject(vertCount, indexCount, color) {
    private fun initObject(){

        this.insertVert(0, Vector3(-1.0, 0.0, -1.0), color)
        this.insertVert(1, Vector3(-1.0, 0.0, 1.0), color)
        this.insertVert(2, Vector3(1.0, 0.0, 1.0), color)
        this.insertVert(3, Vector3(1.0, 0.0, -1.0), color)

        this.insertIndexes(
            shortArrayOf(
                0, 1, 3,
                3, 2, 1
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

    constructor(vec3: Vector3): this(){
        initObject()
        this.position = vec3
    }

    constructor(vec3: Vector3, scale: Double): this(){
        this.position = vec3
        this.scale = scale
        initObject()
    }

    constructor(vec3: Vector3, col: Vector4): this(4, 6, col){
        initObject()
        this.position = vec3
    }
}