import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.camera.*
import org.openrndr.extra.color.presets.ORANGE
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.internal.Driver
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.math.transforms.translate
import org.openrndr.shape.Rectangle
import kotlin.math.sin

/**
 *  This is a template for a live program.
 *
 *  It uses oliveProgram {} instead of program {}. All code inside the
 *  oliveProgram {} can be changed while the program is running.
 */

fun main() = application {
    configure {
        width = 1024
        height = 768
        title = "OPENRNDR LIVE"
        vsync = true
        hideWindowDecorations = false
        hideCursor = true

    }

    oliveProgram {
        val rt = renderTarget(160*2, 120*2, multisample = BufferMultisample.Disabled) {
            colorBuffer()
            depthBuffer()
        }.apply {
            colorBuffer(0).filter(MinifyingFilter.LINEAR, MagnifyingFilter.NEAREST)
            depthBuffer(0, 0, multisample = BufferMultisample.Disabled)
        }

        val obj = TestPyramidObject(Vector3(0.0, 0.0, 0.0))
        val obj2 = TestPyramidObject(Vector3(0.0, 0.0, 6.0))
        val obj3 = TestPyramidObject(Vector3(6.0, 0.0, 6.0))
        val obj4 = TestPyramidObject(Vector3(6.0, 0.0, 0.0))
        val obj5 = TestPyramidObject(Vector3(12.0, 0.0, 0.0))
        val obj6 = TestPyramidObject(Vector3(12.0, 0.0, 6.0))
        val obj7 = TestPyramidObject(Vector3(0.0, 0.0, -6.0))
        val obj8 = TestPyramidObject(Vector3(6.0, 0.0, -6.0))
        val obj9 = TestPyramidObject(Vector3(12.0, 0.0, -6.0))
        val floor = PlaneObject(Vector3(2.5, -0.01, 3.0), 30.0)

        val camera = OrbitalCamera(
            Vector3.UNIT_Z, Vector3.ZERO, 90.0, 0.1, 50.0
        )
        val controls = OrbitalControls(camera, keySpeed = 1.0)

        extend(controls)

        val renderables = listOf(floor, obj, obj2, obj3, obj4, obj5, obj6, obj7,obj8, obj9)
        var lastTick = 0.0

        extend {
            val now = seconds
            val delta = now - lastTick
            renderables.forEach { it.tick(delta) }
            lastTick = now
            //TODO: function
            renderObjects(rt, program, camera, renderables)
            obj.hover(0.0, 5.0, seconds)
            obj.spin(90.0)
            //TODO: end todo

            drawer.image(rt.colorBuffer(0), 0.0, 0.0, program.width.toDouble(), program.height.toDouble())
            rt.clearColor(0, ColorRGBa.TRANSPARENT)
            rt.clearDepth(0.0, 0)
        }
    }
}

fun renderObjects(rt: RenderTarget, program: Program, camera: OrbitalCamera, objects: List<RenderObject>){
    with(program) {
        drawer.isolatedWithTarget(rt) {
            drawer.clear(ColorRGBa.CYAN) //SkyColor (NECESSARY)
            camera.beforeDraw(this, program)
            objects.forEach {
                with(it) {
                    render(this@isolatedWithTarget)
                }
            }
            camera.afterDraw(this, program)
        }
    }
}
