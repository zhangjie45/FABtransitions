package adik.fabtransitions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import com.nineoldandroids.animation.Animator;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;


public class MaterialSheet {

    private Activity activity;
    private  Drawable drawable;
    private    int color = Color.WHITE;
    private     int Size = 72;
    private FloatingActionButton fab;
    private int  fabX,fabY;
    private   float  revealX, revealY;
    private   float revealRadius;
    private SupportAnimator revealAnimator;
    private ArcAnimator revealArc;
    private   FrameLayout frame;
    private View overlay;

    public  MaterialSheet(final View view){
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = (Gravity.END);
        params.setMargins(0, 550, 30, 30);
        view.setLayoutParams(params);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                revealX  = (view.getLeft() + view.getRight()) / 2;
                revealY  = (view.getTop() + view.getBottom()) / 2;
                revealRadius = Math.max(view.getWidth(), view.getHeight());
                fabX = (fab.getLeft() +fab.getRight()) / 2;
                fabY = (fab.getTop() + fab.getBottom()) / 2;


            }
        });

    }


    public void Fab(Activity act,Drawable draw,int clr,int size){
        activity = act;
        drawable = draw;
        color = clr;
        Size = size;
    }

    public FloatingActionButton Create(){
        fab = new FloatingActionButton.Builder(activity)
                .withDrawable(drawable)
                .withButtonColor(color)
                .withMargins(0,0,16,16)
                .withGravity(Gravity.END | Gravity.BOTTOM)
                .create();
        return fab;

    }

    public  boolean Reveal(final View view){
        int id =   ((View)view.getParent()).getId();
        frame = (FrameLayout)activity.findViewById(id);
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        overlay  = inflater.inflate(R.layout.dim,frame,false);

        frame.addView(overlay,0);
        view.setVisibility(View.VISIBLE);
        revealAnimator = ViewAnimationUtils.createCircularReveal(view, (int)revealX, (int)revealY+245, 0, revealRadius);
        revealAnimator.setDuration(210);
        revealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        revealArc = ArcAnimator.createArcAnimator(fab,revealX,revealY+245, +10, Side.LEFT);
        revealArc.setDuration(120);
        revealArc.setInterpolator(new AccelerateDecelerateInterpolator());
        revealArc.start();
        revealArc.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                fab.hideFloatingActionButton();
                                fab.setVisibility(View.INVISIBLE);
                                revealAnimator.start();

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

        return true;

    }

    public boolean HideReveal( final View Rootview) {

                frame.removeView(overlay);
                revealAnimator = ViewAnimationUtils.createCircularReveal(Rootview, (int) revealX, (int) revealY + 245, revealRadius, 0);
                revealAnimator.setDuration(210);
                revealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                revealArc = ArcAnimator.createArcAnimator(fab, fabX, fabY,-10, Side.LEFT);
                revealArc.setDuration(120);
                revealArc.setInterpolator(new AccelerateDecelerateInterpolator());
                revealAnimator.start();
                revealAnimator.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        Rootview.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        fab.showFloatingActionButton();
                        revealArc.start();

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });

        return  true;

    }


}
