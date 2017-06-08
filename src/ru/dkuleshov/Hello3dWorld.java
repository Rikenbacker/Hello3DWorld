package ru.dkuleshov;

import com.threed.jpct.*;
import java.io.*;
import com.threed.jpct.util.*;
import java.awt.*;
import java.awt.event.*;

import ru.dkuleshov.C3DObject.*;
import ru.dkuleshov.service.*;
import ru.dkuleshov.gameObject.*;

public class Hello3dWorld
{
    private Terrain terr = null;
    private float xAngle = 0;
    private World world;
    private FrameBuffer buffer;
    private Object3D box;
    private Camera camera=null;

    private TextureManager texMan=null;
    private Texture numbers=null;

    private FpsCounter fpsCnt = null;
    private KeyMapper keyMapper=null;
    private MouseMapper mouseMapper = null;
    private int width=800;
    private int height=600;
    private boolean isExit = false;
    Rail rail = null;

    private Ticker ticker = new Ticker(15);

    private boolean wireframe=false;

    private boolean left=false;
    private boolean right=false;
    private boolean forward=false;
    private boolean back=false;
    private boolean up=false;
    private boolean down=false;


    private RailLine railLine1 = null;
    private RailLine railLine2 = null;
    private RailLine railLine3 = null;
    private RailLine railLine4 = null;


    public Hello3dWorld() throws Exception
    {
        fpsCnt = new FpsCounter();
        world = new World();
        world.setAmbientLight(256, 255, 256);

        char dirSeparator = File.separatorChar;
        TextureManager.getInstance().addTexture("box", new Texture("textures"+ dirSeparator + "box.jpg"));

        texMan = TextureManager.getInstance();
        numbers=new Texture("textures"+ dirSeparator + "font" + dirSeparator + "numbers.jpg");
        texMan.addTexture("numbers", numbers);
        texMan.addTexture("rocks", new Texture("textures" + dirSeparator + "rocks.jpg"));
        texMan.addTexture("grid", new Texture("textures" + dirSeparator + "grid.jpg"));

        terr = new Terrain(world);
        terr.create();

        rail = new Rail(world);
        rail.create();

        camera = world.getCamera();
        camera.setPosition(5, 0f, -5);
//        camera.lookAt(terr.getTransformedCenter());

        World.setDefaultThread(Thread.currentThread());

        buffer = new FrameBuffer(width, height, FrameBuffer.SAMPLINGMODE_NORMAL);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.setBoundingBoxMode(FrameBuffer.BOUNDINGBOX_NOT_USED);
        buffer.optimizeBufferAccess();
        keyMapper=new KeyMapper();
        mouseMapper = new MouseMapper(buffer);
        mouseMapper.hide();

//        railLine1 = new RailLine(new SimpleVector(500f, 100f, 500f), new SimpleVector(-500f, 100f, -500f), world);
        railLine1 = new RailLine(new SimpleVector(500f, 100f, 500f), new SimpleVector(300f, 100f, 300f), world);
        railLine1.create();
        railLine2 = new RailLine(railLine1.getConnectorTwo(), new SimpleVector(0f, 100f, 0f), world);
        railLine2.create();
        railLine3 = new RailLine(railLine2.getConnectorTwo(), new SimpleVector(-200f, 100f, -200f), world);
        railLine3.create();
        railLine4 = new RailLine(railLine3.getConnectorTwo(), new SimpleVector(-500f, 100f, -500f), world);
        railLine4.create();
    }

    public void loop() throws Exception
    {
        long ticks = 0;

        while (!org.lwjgl.opengl.Display.isCloseRequested() && !isExit)
        {
            ticks = ticker.getTicks();
            if (ticks > 0)
            {
                poll();
                move(ticks);
            }

            buffer.clear(java.awt.Color.BLUE);
            world.renderScene(buffer);

            if (!wireframe)
                world.draw(buffer);
            else
                world.drawWireframe(buffer, Color.white);

            buffer.update();

            fpsCnt.Tick();

            display();
            Thread.sleep(10);
            //Thread.yield();
        }
        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
        System.exit(0);
    }

    private void move(long ticks)
    {
        if (ticks == 0)
            return;

        rail.rotate(0f, 0.1f, 0f);

        // Key controls
        SimpleVector ellipsoid = new SimpleVector(5, 5, 5);

        if (forward)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEIN, ellipsoid, ticks, 5);

        if (back)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEOUT, ellipsoid, ticks, 5);

        if (left)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVELEFT, ellipsoid, ticks, 5);

        if (right)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVERIGHT, ellipsoid, ticks, 5);

        if (up)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEUP, ellipsoid, ticks, 5);

        if (down)
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEDOWN, ellipsoid, ticks, 5);

        // mouse rotation
        Matrix rot = world.getCamera().getBack();
        int dx = mouseMapper.getDeltaX();
        int dy = mouseMapper.getDeltaY();

        float ts = 0.2f * ticks;
        float tsy = ts;

        if (dx != 0)
            ts = dx / 500f;

        if (dy != 0)
            tsy = dy / 500f;

        if (dx != 0)
            rot.rotateAxis(rot.getYAxis(), ts);

        if ((dy > 0 && xAngle < Math.PI / 2) || (dy < 0 && xAngle > -Math.PI / 2))
        {
            rot.rotateX(tsy);
            xAngle += tsy;
        }

        world.getCamera().setBack(world.getCamera().getBack().cloneMatrix());
    }

    private void display()
    {
        showNumber(fpsCnt.getFps(), 5, 2);
        showNumber(world.getVisibilityList().getSize(), 5, 12);
        buffer.displayGLOnly();
    }

    private void showNumber(int number, int x, int y)
    {
        if (numbers!=null)
        {
            String sNum=Integer.toString(number);
            for (int i=0; i<sNum.length(); i++)
            {
                char cNum=sNum.charAt(i);
                int iNum=cNum-48;
                buffer.blit(numbers, iNum*5, 0, x, y, 5, 9, FrameBuffer.TRANSPARENT_BLITTING);
                x+=5;
            }
        }
    }

    private void poll()
    {
        KeyState state=null;
        do
        {
            state=keyMapper.poll();
            if (state!=KeyState.NONE)
                keyAffected(state);
        } while (state!=KeyState.NONE);
    }

    private void keyAffected(KeyState state)
    {
        int code=state.getKeyCode();
        boolean event = state.getState();

        switch (code)
        {
            case (KeyEvent.VK_ESCAPE):
                isExit = event;
                break;
            case (KeyEvent.VK_A):
                left = event;
                break;
            case (KeyEvent.VK_D):
                right = event;
                break;
            case (KeyEvent.VK_W):
                forward = event;
                break;
            case (KeyEvent.VK_S):
                back = event;
                break;
            case (KeyEvent.VK_E):
                up = event;
                break;
            case (KeyEvent.VK_F):
                down = event;
                break;
            case (KeyEvent.VK_Q):
                if (event)
                    wireframe =! wireframe;
                break;
        }
    }
}
