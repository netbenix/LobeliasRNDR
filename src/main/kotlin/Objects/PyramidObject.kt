package Objects

import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import java.lang.Math.sin

open class PyramidObject(vertCount: Int = 5, indexCount: Int = 18, color: Vector4 = Vector4.ONE) : RenderObject(vertCount, indexCount, color) {
    private fun initObject(){
        vertices[0].position = Vector3(-1.0,0.0,1.0)
        vertices[1].position = Vector3(1.0,0.0,1.0)
        vertices[2].position = Vector3(1.0,0.0,-1.0)
        vertices[3].position = Vector3(-1.0,0.0,-1.0)
        vertices[4].position = Vector3(0.0, 2.0, 0.0)

        calculateNormals()
        this.fillColor(color)
        this.insertVerts()

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
    }

    constructor(vec3: Vector3): this(){
        this.position = vec3
        initObject()
    }

    constructor(vec3: Vector3, col: Vector4): this(5, 18, col){
        this.position = vec3
        initObject()
    }

    constructor(vec3: Vector3, col: Vector4, scale: Vector3): this(5, 18, col){
        this.scale = scale
        this.position = vec3
        initObject()
    }



}