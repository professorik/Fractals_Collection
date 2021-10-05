def spyral(n):
    import turtle as t
    t.shape("turtle")
    t.delay(0)
    for i in range(100,n):
        t.forward(i/1000)
        t.left(1)

def polygon(n):
    import turtle as t
    for i in range(0,n):
        t.forward(10)
        t.left(360/n)

def chess():
    import turtle as t
    t.shape("square")
    t.up()
    t.delay(0)
    t.turtlesize(2,2,2)
    A=[]
    for i in range(64):
        A.append(t.clone())
        if i%2 - int(i/8)%2 == 0 :
            A[i].color("white")
    for i in range(64):
        A[i].goto(40*(i%8),40*(int(i/8)))

def random(n):
    import turtle as t
    import random as r
    for i in range(n):
        d = r.random()
        s = r.random()
        if d < 1/2:
            t.left(360*d)
        else:
            t.right(360*(1-d))
        t.forward(40*s)
    t.mainloop()
        


random(10000)
