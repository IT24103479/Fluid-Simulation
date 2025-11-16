package org.example.fluidsim;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public List<Particle> particles=new ArrayList<>();

    private final double gravity=300;

    public Simulation() {

        for(int i=0;i<200;i++){
            double x=Math.random()*800;
            double y=Math.random()*600;
            particles.add(new Particle(x,y));
        }
    }

    public void update(double dt){
        for(Particle p:particles){

            p.vy+=gravity*dt;

            p.x += p.vx*dt;
            p.y += p.vy*dt;

            if(p.y>580){
                p.y=580;
                p.vy*= -0.4;//energy loss
            }

        }
    }
}
