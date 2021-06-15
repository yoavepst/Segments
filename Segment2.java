/**
 * This class represents a segment 
 *
 * @author (Yoav Epstein)
 * @version (16/11/2020)
 */
public class Segment2
{
    // instance variables
    Point _poCenter;
    double _length;

    private final double NOT_MOVING = 0;// Used in 'move' methods
    private final double NO_OVERLAP = 0;// Used in overlap method

    // Constructors 
    /**
     *Constructs a new segment using a center point and the segment length.
     * @param left The left edge of the segment
     * @param right The right edge of the segment
     * 
     */
    public Segment2 (Point poCenter, double length)
    {        
        _length = length;
        _poCenter = new Point (poCenter);
    }   

    /**
     * Constructs a new Segment by decoding the specified two points.
     * @param left The left edge of the segment
     * @param right The right edge of the segment
     * 
     */
    public Segment2 (Point left, Point right)
    {
        Point poLeft = new Point (left);
        Point poRight = new Point (right);
        if (poRight.isAbove(poLeft) || poRight.isUnder(poLeft))
            poRight.setY(poLeft.getY());

        _length = poLeft.distance(poRight);
        _poCenter = new Point (findCenter (poLeft, poRight));

    }   

    private Point findCenter (Point p1, Point p2)
    {
        double x,y;
        // The middle is c-p1=p2-c -> 2c=p1+p2 -> c=(p1+p2)/2
        x = (p2.getX() + p1.getX()) / 2.0;
        y = (p2.getY() + p1.getY()) / 2.0;
        Point p = new Point (x,y);
        return p;
    }

    /**
     * Constructs a new Segment by decoding the specified x & y values of two points.
     * @param leftX The left horizontal edge of the segment
     * @param leftY The left vertical edge of the segment
     * @param rightX The right horizontal edge of the segment
     * @param rightY The right vertical edge of the segment 
     */
    public Segment2(double leftX ,double leftY, double rightX ,double rightY)
    {
        Point poLeft = new Point (leftX, leftY);

        if (rightY != leftY)
            rightY = leftY;
        Point poRight = new Point (rightX, rightY);

        _length = poLeft.distance(poRight);
        _poCenter = new Point (findCenter (poLeft, poRight));;
    }

    /**
     * Constructs a new segment by decoding the specified two points copying 
     * the values form the other segment
     * @param other The other segment
     * 
     */
    public Segment2 (Segment2 other)
    {
        _length = other._length;
        _poCenter = new Point (other._poCenter);
    }

    // Methods
    /**
     * Gets the left point of the segment
     * @return The left point of the segment
     */
    public Point getPoLeft()
    {
        // The left point is in length/2 less from the center 
        return new Point (_poCenter.getX() - _length / 2.0, _poCenter.getY()); 
    }

    /**
     * Gets the right point of the segment
     * @return The right point of the segment
     */
    public Point getPoRight()
    {
        // The right point is in length/2 more from the center 
        return new Point (_poCenter.getX()+_length/2.0,_poCenter.getY()); 
    }

    /**
     * Gets the length (distance between egdes) of the segment
     */
    public double getLength()
    {
        return _length;
    }

    /**
     * returns a string that represents this segment.
     * @return String that represents this segment
     * in the following format:
     * (x,y)---(z,w)
     */
    public String toString ()
    {
        String left, right;
        left = getPoLeft().toString();
        right = getPoRight().toString();
        return left + "---" + right;    
    }

    /** 
     * Checks if 2 segments are equal (the same)
     * @param other The segment to compare this segment to
     * @return True if the segments are the same
     */
    public boolean equals (Segment2 other)
    {
        return _poCenter.equals(other._poCenter) && _length == other._length;
    }

    /** 
     *  Checks if this segment is above other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is above other segment
     */
    public boolean isAbove (Segment2 other)
    {
        return _poCenter.isAbove(other._poCenter);
    }

    /** 
     *  Checks if this segment is under other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is under other segment
     */
    public boolean isUnder (Segment2 other)
    {
        return other._poCenter.isAbove(_poCenter);
    }

    /** 
     *  Check if this segment is located completely from the left hand side of other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is located from the left hand side other segment
     */
    public boolean isLeft (Segment2 other)
    {
        if (getPoRight().isLeft(other.getPoLeft()))
            return true;
        return false;
    }

    /** 
     *  Check if this segment is located completely from the right hand side of other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is located from the right hand side other segment
     */
    public boolean isRight (Segment2 other)
    {
        if (other.getPoRight().isLeft(getPoLeft()))
            return true;
        return false;
    }

    /**
     * Shifting the segment horizontal.
     * @param delta The horizontal shifting distance
     */
    public void moveHorizontal (double delta)
    {        
        _poCenter.move(delta,NOT_MOVING);             
    }

    /**
     * Shifting the segment horizontal.
     * @param delta The horizontal shifting distance
     */
    public void moveVertical (double delta)
    {
        _poCenter.move(delta,NOT_MOVING);   
    }

    /**
     * Changing the size the segment decoding its right edge.
     * @param delta The size changing value of the segment
     */
    public void changeSize (double delta)
    {        
        getPoRight().move(delta,NOT_MOVING);
        if (getPoRight().isLeft(getPoLeft()))
            getPoRight().move(-delta,NOT_MOVING);
    }

    /** 
     *  Check if point p is on this segment.
     *  @param p The point to be checked
     *  @return True if point p is on this segment
     */
    public boolean pointOnSegment (Point p)
    {
        if (_poCenter.isUnder(p) || _poCenter.isAbove(p))
            return false;
        else if (getPoRight().isLeft(p) || getPoLeft().isRight(p))
            return false;
        return true;
    }

    /** 
     * Checks if this segment is bigger then other segment.
     * @param other The segment to compare this segment to
     * @return True if this segment is bigger
     */
    public boolean isBigger (Segment2 other)
    {
        return _length > other._length;
    }

    // Checking whether an overlap is occurring
    private boolean isOverlap (Segment2 other)
    {
        Segment2 thisTemp = new Segment2(this);
        Segment2 otherTemp = new Segment2(other);

        if (thisTemp.isRight(otherTemp) || thisTemp.isLeft(otherTemp))
            return false;
        // Checking the sigle point overlap case
        else if (thisTemp.getPoLeft().getX() == otherTemp.getPoRight().getX() ||
        thisTemp.getPoRight().getX() == otherTemp.getPoLeft().getX())
            return false;
        return true;
    }

    private double minPointX (Point p1, Point p2)
    {
        if(p1.isLeft(p2))
            return p1.getX();
        if(p1.isRight(p2))
            return p2.getX();
        return p2.getX();
    }

    private double maxPointX (Point p1, Point p2)
    {
        if(p1.isLeft(p2))
            return p2.getX();
        if(p1.isRight(p2))
            return p1.getX();
        return p2.getX();
    }

    /** 
     * Checks what is the overlep between this segment to another segment.
     * @param other The segment to be chaeked
     * @return The length of the segments overlap
     */
    public double overlap (Segment2 other)
    {
        if (isOverlap(other)){
            double a = maxPointX (getPoLeft(), other.getPoLeft());
            double b = minPointX (getPoRight(), other.getPoRight());
            // The overlap is in between the max from the left and the min drom the right
            return b-a;
        }
        return NO_OVERLAP;
    }

    /** 
     * Checks what is the perimeter of a trapeze that is created between ths and other segment .
     * @param other The segment that is creating a trapeze
     * @return The perimeter of the trapeze 
     */
    public double trapezePerimeter (Segment2 other)
    {
        double a,b,c,d;
        a = _length;
        b = other._length;
        c = getPoLeft().distance(other.getPoLeft());
        d = getPoRight().distance(other.getPoRight());
        return a + b + c + d;
    }

}

