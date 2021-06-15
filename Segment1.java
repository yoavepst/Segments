/**
 * This class represents a segment 
 *
 * @author (Yoav Epstein)
 * @version (16/11/2020)
 */
public class Segment1
{
    // instance variables
    private Point _poLeft;
    private Point _poRight;

    private final double NOT_MOVING = 0;// Used in 'move' methods
    private final double NO_OVERLAP = 0;// Used in overlap method
    // Constructors 
    /**
     * Constructs a new Segment by decoding the specified two points.
     * @param left The left edge of the segment
     * @param right The right edge of the segment
     * 
     */
    public Segment1 (Point left, Point right)
    {
        _poLeft = new Point (left);
        _poRight = new Point (right);
        if (right.isAbove(left) || right.isUnder(left))
            _poRight.setY(_poLeft.getY());
    }   

    /**
     * Constructs a new Segment by decoding the specified x & y values of two points.
     * @param leftX The left horizontal edge of the segment
     * @param leftY The left vertical edge of the segment
     * @param rightX The right horizontal edge of the segment
     * @param rightY The right vertical edge of the segment 
     */
    public Segment1(double leftX ,double leftY, double rightX ,double rightY)
    {
        _poLeft = new Point (leftX, leftY);

        if (rightY != leftY)
            rightY = leftY;
        _poRight = new Point (rightX, rightY);     
    }

    /**
     * Constructs a new segment by decoding the specified two points copying 
     * the values form the other segment
     * @param other The other segment
     * 
     */
    public Segment1 (Segment1 other)
    {
        _poLeft = new Point (other._poLeft);
        _poRight = new Point (other._poRight);
    }

    // Methods
    /**
     * Gets the left point of the segment.
     * @return The left point of the segment

     */
    public Point getPoLeft()
    {
        return new Point (_poLeft); 
    }

    /**
     * Gets the right point of the segment
     * @return The right point of the segment
     */
    public Point getPoRight()
    {
        return new Point (_poRight); 
    }

    /**
     * Gets the length (distance between egdes) of the segment
     */
    public double getLength()
    {
        return _poRight.distance(_poLeft);
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
        left = _poLeft.toString();
        right = _poRight.toString();
        return left + "---" + right;    
    }

    /** 
     * Checks if 2 segments are equal (the same)
     * @param other The segment to compare this segment to
     * @return True if the segments are the same
     */
    public boolean equals (Segment1 other)
    {
        return _poLeft.equals(other._poLeft) && _poRight.equals(other._poRight);
    }

    /** 
     *  Checks if this segment is above other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is above other segment
     */
    public boolean isAbove (Segment1 other)
    {
        return _poLeft.isAbove(other._poLeft);// In according to Segment1's contruction
    }

    /** 
     *  Checks if this segment is under other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is under other segment
     */
    public boolean isUnder (Segment1 other)
    {
        return other._poLeft.isAbove(_poLeft);
    }

    /** 
     *  Check if this segment is located from the left hand side of other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is located from the left hand side other segment
     */
    public boolean isLeft (Segment1 other)
    {
        if (_poRight.isLeft(other._poLeft))
            return true;
        return false;
    }

    /** 
     *  Check if this segment is located from the right hand side of other segment.
     *  @param other The segment to compare this segment to
     *  @return True if this segment is located from the right hand side other segment
     */
    public boolean isRight (Segment1 other)
    {
        if (other._poRight.isLeft(_poLeft))
            return true;
        return false;
    }

    /**
     * Shifting the segment horizontal.
     * @param delta The horizontal shifting distance
     */
    public void moveHorizontal (double delta)
    {    
        // In according to Point's move method no further verifying is needed
        _poLeft.move(delta,NOT_MOVING);
        _poRight.move(delta,NOT_MOVING);         
    }

    /**
     * Shifting the segment horizontal.
     * @param delta The horizontal shifting distance
     */
    public void moveVertical (double delta)
    {
        // In according to Point's move method no further verifying is needed
        _poLeft.move(NOT_MOVING, delta);
        _poRight.move(NOT_MOVING, delta);  
    }

    /**
     * Changing the size the segment decoding its right edge.
     * @param delta The size changing value of the segment
     */
    public void changeSize (double delta)
    {
        _poRight.move(delta,NOT_MOVING);
        if (_poRight.isLeft(_poLeft))
            _poRight.move(-delta,NOT_MOVING);
    }

    /** 
     *  Check if point p is on this segment.
     *  @param p The point to be checked
     *  @return True if point p is on this segment
     */
    public boolean pointOnSegment (Point p)
    {
        if (_poLeft.isUnder(p) || _poLeft.isAbove(p))
            return false;
        else if (_poRight.isLeft(p) || _poLeft.isRight(p))
            return false;
        return true;
    }

    /** 
     * Checks if this segment is bigger then other segment.
     * @param other The segment to compare this segment to
     * @return True if this segment is bigger
     */
    public boolean isBigger (Segment1 other)
    {
        return getLength() > other.getLength();
    }

    // Checking whether an overlap is occurring
    private boolean isOverlap (Segment1 other)
    {
        Segment1 thisTemp = new Segment1(this);
        Segment1 otherTemp = new Segment1(other);

        if (thisTemp.isRight(otherTemp) || thisTemp.isLeft(otherTemp))
            return false;
        // Checking the sigle point overlap case
        else if (thisTemp._poLeft.getX() == otherTemp._poRight.getX() ||
        thisTemp._poRight.getX() == otherTemp._poLeft.getX())
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
    public double overlap (Segment1 other)
    {
        if (isOverlap(other)){
            double a = maxPointX (_poLeft, other._poLeft);
            double b = minPointX (_poRight, other._poRight);
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
    public double trapezePerimeter (Segment1 other)
    {
        double a,b,c,d;
        a = getLength();
        b = other.getLength();
        c = _poLeft.distance(other._poLeft);
        d = _poRight.distance(other._poRight);
        return a + b + c + d;
    }

}

