package Objects

import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class CubeObject(vertCount: Int = 8, indexCount: Int = 36, color: Vector4 = Vector4.ONE): RenderObject(vertCount, indexCount, color) {

    private fun initObject(){

        vertices[0].position = Vector3(-1.0, -1.0, -1.0)
        vertices[1].position = Vector3(-1.0, 1.0, -1.0)
        vertices[2].position = Vector3(1.0, 1.0, -1.0)
        vertices[3].position = Vector3(1.0, -1.0, -1.0)
        vertices[4].position = Vector3(1.0, -1.0, 1.0)
        vertices[5].position = Vector3(1.0, 1.0, 1.0)
        vertices[6].position = Vector3(-1.0, 1.0, 1.0)
        vertices[7].position = Vector3(-1.0, -1.0, 1.0)

        calculateNormals()
        fillColor(color)
        insertVerts()

        this.insertIndexes(
            shortArrayOf(
                0, 1, 2,
                2, 3, 0,
                0, 1, 6,
                6, 0 ,7,
                7, 6, 5,
                5, 7, 4,
                4, 3, 2,
                2, 5, 4,
                4, 7, 0,
                0, 3, 4,
                1, 6, 5,
                5, 2, 1
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

    constructor(vec3: Vector3, col: Vector4): this(8, 36, col){
        this.position = vec3
        initObject()
    }

    constructor(pos: Vector3, scale: Vector3, col: Vector4): this(8, 36, col){
        this.position = pos
        this.scale = scale
        initObject()
    }

    constructor(pos: Vector3, col: Vector4, rot: Vector3): this(8, 36, col){
        this.position = pos
        this.rotation = rot
        initObject()
    }
    constructor(pos: Vector3, scale: Vector3, col: Vector4, rot: Vector3): this(8, 36, col){
        this.position = pos
        this.rotation = rot
        this.scale = scale
        initObject()
    }


}