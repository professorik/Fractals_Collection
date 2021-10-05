import turtle as t
import random as r
from math import pi, sin, cos 

def poligon(n):
    t.shape("turtle")
    t.color("red")
    t.delay(5)

    for i in range(n):
        t.forward(100)
        t.right(360/n)
    t.mainloop()

def spiral():
    t.shape("turtle")
    t.color("red")
    t.delay(5)

    for i in range(2000):
        d = i / 10 * pi
        dx = d * cos(d)
        dy = d * sin(d)
        t.goto(dx, dy)
    t.mainloop()

def chessBoard():
    t.shape("square")
    A = []
    t.delay(0)
    t.up()
    t.turtlesize(2)
    for i in range(8):
        for j in range(8):
            A.append(t.clone())
            A[i*8+j].color("blue" if (i+j)%2 == 0 else "red")
            A[i*8+j].goto(i*40, j*40)
    t.mainloop()    

def randWalking():
    t.shape("turtle")
    t.color("red")
    t.delay(5)

    for i in range(100):
        d = r.random()
        if d < 1/2:
            t.left(360*d)
        else:
            t.right(360*(1-d))
        t.forward(100*r.random())
    t.mainloop()

def pythTree(t, xA, yA, size, angle, s):
    if s > 0:
        t.turtlesize(size)
        t.right(angle)
        t.goto(xA, yA)
        pythTree(t.clone(), xA+20*size*cos(angle), yA+20*size*sin(angle), size/1.1, -60, s-1)
        #pythTree(t.clone(), xA - 40, yA, size/1.1, 60, s-1)
        #t.stump()



if __name__ == "__main__":
    #spiral()
    #poligon(6)
    #chessBoard()
    t.shape("square")
    t.delay(0)
    t.up()
    pythTree(t.clone(), 0, 0, 2, 0, 10)
    t.mainloop()
    #randWalking()