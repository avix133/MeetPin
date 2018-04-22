import android.app.Activity
import android.content.ClipData
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnDragListener
import com.coding.team.meetpin.R


class PinActivity : Activity() , OnDragListener {
    var pin =findViewById<View>(R.id.pin)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        //pin = findViewById<View>(R.id.pin)
        pin.setOnTouchListener( View.OnTouchListener(function = {view, motionEvent ->
//             fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                if (motionEvent.action == MotionEvent.ACTION_DOWN){
                    var shadowBuilder = View.DragShadowBuilder(view)
                    view.startDrag(null, shadowBuilder, view, 0)
                    view.visibility = View.INVISIBLE
                    return@OnTouchListener true
                }else{
                    return@OnTouchListener false
                }
            })
        )

    }



    override fun onDrag(v: View, event: DragEvent): Boolean {
        var result = true

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if(event.localState==null){
                    result=false
                }else{

                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {

            }
            DragEvent.ACTION_DRAG_EXITED -> {

            }
            DragEvent.ACTION_DROP -> {

            }
            DragEvent.ACTION_DRAG_ENDED -> {
            }
            else -> {
            }
        }// do nothing
        return result
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//        var listener = View.OnTouchListener(function = {view, motionEvent ->
//
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//
//                view.y = motionEvent.rawY - view.height/2
//                view.x = motionEvent.rawX - view.width/2
//            }
//
//            true
//
//        })
//        var pin = findViewById<View>(R.id.pin)
//        pin.setOnTouchListener(listener)
//
//}
}
