package Objects

import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class PlaneObject(vertCount: Int = 4, indexCount: Int = 6, color: Vector4 = Vector4(1.0)): RenderObject(vertCount, indexCount, color) {
    private fun initObject(){

        vertices[0].position = Vector3(-1.0, 0.0, -1.0) * scale
        vertices[1].position = Vector3(-1.0, 0.0, 1.0) * scale
        vertices[2].position = Vector3(1.0, 0.0, 1.0) * scale
        vertices[3].position = Vector3(1.0, 0.0, -1.0) * scale

        calculateNormals()
        fillColor(color)
        insertVerts()


        this.insertIndexes(
            shortArrayOf(
                0, 1, 3,
                3, 2, 1
            )
        )
    }

    constructor(vec3: Vector3): this(){
        this.position = vec3
        initObject()
    }

    constructor(vec3: Vector3, scale: Vector3): this(){
        this.position = vec3
        this.scale = scale
        initObject()
    }

    constructor(vec3: Vector3, col: Vector4): this(4, 6, col){
        this.position = vec3
        initObject()
    }

    constructor(vec3: Vector3, col: Vector4, scale: Vector3): this(4, 6, col){
        this.position = vec3
        this.scale = scale
        initObject()
    }
}