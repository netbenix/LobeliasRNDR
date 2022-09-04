package Objects

import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class TestPyramidObject(vertCount: Int = 5, indexCount: Int = 18, color: Vector4 = Vector4.ZERO) : RenderObject(vertCount, indexCount, color) {
    private fun initObject(){

        vertices[0].position = Vector3(-1.0,0.0,1.0) * scale
        vertices[0].color = Vector4(1.0,0.0,0.0,1.0)
        vertices[1].position = Vector3(1.0,0.0,1.0) * scale
        vertices[1].color = Vector4(0.0,0.0,1.0,1.0)
        vertices[2].position = Vector3(1.0,0.0,-1.0) * scale
        vertices[2].color = Vector4(0.0,1.0,0.0,1.0)
        vertices[3].position = Vector3(-1.0,0.0,-1.0) * scale
        vertices[3].color = Vector4(1.0,0.0,1.0,1.0)
        vertices[4].position = Vector3(0.0, 2.0, 0.0) * scale
        vertices[4].color = Vector4(0.5, 0.5, 0.5, 1.0)

        //calculateNormals()
        insertVerts()

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

    constructor(vec3: Vector3, scale: Vector3): this(5, 18){
        this.position = vec3
        this.scale = scale
        initObject()
    }

}