import android.app.Activity
import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnDragListener
import com.coding.team.meetpin.R


class PinActivity : Activity(), OnDragListener {
    var pin =findViewById<View>(R.id.pin)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        var pin = findViewById<View>(R.id.pin)
        pin.setOnDragListener(this)
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

}
