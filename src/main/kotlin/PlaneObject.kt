import org.openrndr.Program
import org.openrndr.draw.DrawPrimitive
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

class PlaneObject(vertCount: Int = 4, indexCount: Int = 6):RenderObject(vertCount, indexCount) {
    private fun initObject(){
        val color = Vector4(0.5,0.5,0.5,1.0)

        this.insertVert(0, Vector3(-1.0*scale, 0.0*scale, -1.0*scale), color)
        this.insertVert(1, Vector3(-1.0*scale, 0.0*scale, 1.0*scale), color)
        this.insertVert(2, Vector3(1.0*scale, 0.0*scale, 1.0*scale), color)
        this.insertVert(3, Vector3(1.0*scale, 0.0*scale, -1.0*scale), color)

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

    constructor(vec3: Vector3): this(4, 6){
        initObject()
        this.position = vec3
    }

    constructor(vec3: Vector3, scale: Double): this(4, 6){
        this.position = vec3
        this.scale = scale
        initObject()
    }

    override fun Program.render(drawer: Drawer) {
        drawer.isolated {
            shadeStyle = shader
            translate(position)
            vertexBuffer(indexBuff, listOf(vertBuff), DrawPrimitive.TRIANGLES)
            translate(-position)
        }
    }
}

interface Tickable {
    fun tick(delta: Double)
}