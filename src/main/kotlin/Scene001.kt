import Objects.CubeObject
import Objects.PlaneObject
import Objects.PyramidObject
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.camera.OrbitalCamera
import org.openrndr.extra.camera.OrbitalControls
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

fun main () = application {
    configure {
        width = 1024
        height = 768
        title = "LobeliasRNDR - Scene 001"
        vsync = true
        hideWindowDecorations = false
        hideCursor = true
    }


    oliveProgram {
        val rt = renderTarget(320*4, 240*4, multisample = BufferMultisample.Disabled){
            colorBuffer()
            depthBuffer()
        }. apply {
            colorBuffer(0).filter(MinifyingFilter.LINEAR, MagnifyingFilter.NEAREST)
            depthBuffer(0, 0, multisample = BufferMultisample.Disabled)
        }

        var lastTick = 0.0

        //REQUIRED OBJECTS
        val camera = OrbitalCamera(
            Vector3.UNIT_Z, Vector3.ZERO, 90.0, 0.1, 50.0
        )
        val controls = OrbitalControls(camera, keySpeed = 1.0)
        extend(controls)

        //COLOR CODES = https://antongerdelan.net/colour/
        //SCENE OBJECTS
        val floor = PlaneObject(Vector3(0.0), Vector4(0.5, 0.5, 0.5, 1.0), Vector3(20.0, 1.0, 20.0))

        //ROOM 001
        val r001_wallLeft = CubeObject(Vector3(-15.0, 3.01, 1.0), Vector3(0.3, 3.0, 7.0), Vector4(0.381, 0.375, 0.750, 1.0))
        val r001_wallBack = CubeObject(Vector3(-7.70, 3.01, -5.70), Vector3(7.0, 3.0, 0.3), Vector4(0.381, 0.375, 0.750, 1.0))
        val pedestal = CubeObject(Vector3(-8.5, 0.2501, 1.0), Vector3(1.0, 0.25, 1.0), Vector4(0.248, 0.491, 0.540, 1.0))
        val floatingCrystal = PyramidObject(Vector3(-8.5, 0.2501, 1.0), Vector4(0.970, 0.359, 0.705, 0.7), Vector3(0.5))


        val renderables = listOf(floor, r001_wallLeft, r001_wallBack, pedestal, floatingCrystal)

        extend {
            val now = seconds
            val delta = now - lastTick
            renderables.forEach{ it.tick(delta) }
            lastTick = now
            renderObjects(rt, program, camera, renderables)


            //Object transformations
            floatingCrystal.hover(0.5001, 1.0, 0.2, seconds)
            floatingCrystal.spin(90.0)




            drawer.image(rt.colorBuffer(0), 0.0, 0.0, program.width.toDouble(), program.height.toDouble())
            rt.clearColor(0, ColorRGBa.TRANSPARENT)
            rt.clearDepth(0.0, 0)

        }

    }
}