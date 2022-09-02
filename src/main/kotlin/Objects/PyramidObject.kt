package Objects

import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.lang.Math.sin

class PyramidObject(vertCount: Int = 5, indexCount: Int = 18, color: Vector4 = Vector4(1.0)) : RenderObject(vertCount, indexCount, color) {
    private fun initObject(){
        this.insertVert(0, Vector3(-1.0,0.0,1.0), color)
        this.insertVert(1, Vector3(1.0,0.0,1.0), color)
        this.insertVert(2, Vector3(1.0,0.0,-1.0), color)
        this.insertVert(3, Vector3(-1.0,0.0,-1.0), color)
        this.insertVert(4, Vector3(0.0, 2.0, 0.0), color)

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

    constructor(vec3: Vector3): this(){
        initObject()
        this.position = vec3
    }

    constructor(vec3: Vector3, col: Vector4): this(5, 18, col){
        initObject()
        this.position = vec3
    }



}