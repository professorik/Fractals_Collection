import turtle as t
import random as r
from math import pi, sin, cos, sqrt, acos

def kar():
    C = 800
    xA = -400
    yA = -300
    lx = -400
    ly = -300
    for i in range(5000):
        t.goto(lx, ly)
        t.dot(5, "green")
        a = r.random()
        if 3*a < 1:
            lx = (lx + xA)/2
            ly = (ly + yA)/2
        elif 3*a < 2:
            lx = (lx + C + xA)/2
            ly = (ly + yA)/2
        else:
            lx = (lx + xA + C/2)/2
            ly = (ly + yA + sqrt(3)*C/2)/2

if __name__ == "__main__":
    t.up()
    t.delay(0)
    #kar()
    x = 0
    y = 0
    for i in range(5000):
        t.goto(65*x, 37*y-252)
        t.dot(5, "green")
        a = r.random()
        if a < 0.01:
            x,y = 0.00*x+0.00*y, 0.00*x+0.16*y+0.00
        elif a < 0.86:
            x,y = 0.85*x+0.04*y, -0.04*x+0.85*y+1.60
        elif a < 0.93:
            x,y = 0.20*x-0.26*y, 0.23*x+0.22*y+1.60
        else:
            x,y = -0.15*x+0.28*y, 0.26*x+0.24*y+0.44
    t.mainloop()
