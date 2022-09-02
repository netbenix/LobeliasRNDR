import Objects.RenderObject
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.isolatedWithTarget
import org.openrndr.extra.camera.OrbitalCamera

//TODO: Find nice place for this
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