import turtle as t
import random as r
from math import pi, sin, cos, asin, degrees, sqrt, radians, acos

def poligon(n):
    t.shape("turtle")
    t.color("red")
    t.delay(5)

    for i in range(n):
        t.forward(100)
        t.right(360 / n)
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
            A[i * 8 + j].color("blue" if (i + j) % 2 == 0 else "red")
            A[i * 8 + j].goto(i * 40, j * 40)
    t.mainloop()


def randWalking():
    t.shape("turtle")
    t.color("red")
    t.delay(5)

    for i in range(100):
        d = r.random()
        if d < 1 / 2:
            t.left(360 * d)
        else:
            t.right(360 * (1 - d))
        t.forward(100 * r.random())
    t.mainloop()


colors = ["red", "green", "blue", "orange", "purple", "yellow"]

a = 0.6 #right branch reduction ratio

DS = 20

# t - root node (for related position)
# size - size of root node
# angle - the angle of rotation of the right branch relative to the parent node
# s - current step (depth)
def pythTree(t, size, angle, s):
    if s > 0:
        # right branch
        rB = t.clone()
        rB.color(colors[r.randint(0, len(colors) - 1)])
        rB.turtlesize(size * a)
        rB.left(90)
        BD = DS * size * a / (2 * sin(angle))
        BC = (DS * size * a / 2) + BD * cos(angle)
        AD = size * DS / sqrt(2)
        AB = sqrt(BD * BD + AD * AD - sqrt(2) * BD * AD)
        alpha = pi / 4 - asin(BD / (sqrt(2) * AB))
        rB.right(degrees(alpha))
        rB.forward(AB)
        rB.right(degrees(pi / 2 - alpha - angle))
        rB.forward(BC)
        rB.right(90)
        # left branch
        HD = size * DS * a
        KD = size * DS
        KH = sqrt(HD * HD + KD * KD - 2 * KD * HD * cos(pi / 2 - angle))
        angle2 = pi / 2 - acos((HD * HD - KH * KH - KD * KD) / (-2 * KH * KD))
        lB = t.clone()
        lB.color(colors[r.randint(0, len(colors) - 1)])
        lB.turtlesize(KH / DS)
        lB.left(90)
        BD = KH / (2 * sin(angle2))
        BC = (KH / 2) + BD * cos(angle2)
        AD = size * DS / sqrt(2)
        AB = sqrt(BD * BD + AD * AD - sqrt(2) * BD * AD)
        alpha = pi / 4 - asin(BD / (sqrt(2) * AB))
        lB.left(degrees(alpha))
        lB.forward(AB)
        lB.left(degrees(pi / 2 - alpha - angle2))
        lB.forward(BC)
        lB.right(90)
        #Next step
        pythTree(rB.clone(), size * a, angle, s - 1)
        pythTree(lB.clone(), KH / DS, angle, s - 1)

def pythTreePlug():
    t.Screen().bgcolor("cyan")
    t.up()
    t.shape("square")
    t.delay(0)
    t.setpos(0, -300)
    t.turtlesize(8)
    pythTree(t.clone(), 8, radians(40), 8)
    t.mainloop()

if __name__ == "__main__":
    # spiral()
    # poligon(6)
    # chessBoard()
    # randWalking()
    pythTreePlug()
