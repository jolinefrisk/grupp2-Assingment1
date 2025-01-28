import java.util.HashSet;
import java.util.Set;

public class ConditionsMet {

    // parameters should not be an int but this works for now
    public boolean Condition(int conditionNumber, int parameters, double[] X, double[] Y, int numpoints) {
        switch (conditionNumber) {
            case 0:
                return conditionZero(parameters, X, Y, numpoints);

            case 1:
                return conditionOne(parameters, X, Y, numpoints);

            case 2:
                return conditionTwo(parameters, X, Y, numpoints);

            case 3:
                return conditionThree(parameters, X, Y, numpoints);

            case 4:
                return conditionFour(parameters,X, Y, numpoints);

            case 5:
                return conditionFive(X, numpoints);

            case 6:
                return conditionSix(parameters);

            case 7:
                return conditionSeven(parameters);

            case 8:
                return conditionEight(parameters);

            case 9:
                return conditionNine(parameters);

            case 10:
                return conditionTen(parameters);

            case 11:
                return conditionEleven(parameters);

            case 12:
                return conditionTwelve(parameters);

            case 13:
                return conditionThirteen(parameters);

            case 14:
                return conditionFourteen(parameters);

            // Behöver fixa en faktiskt error hantering och inte bara return false här
            default:
                return false;
        }
    }
    private static double distance(double x1, double y1, double x2, double y2) {
        /*calculates the distance between two datapoints */
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
   
    private boolean conditionZero(Parameters parameters, double[] x, double[] y, int numpoints) {
        /*input param Lenght 1 x,y vectir and numpoint
         * returns true if there exists at least one set of two consecutive data points such that the distance between them is greater than LENGTH1
         * else return false
         */
        if (numpoints < 2) {
            return false; 
        }

        for (int i = 0; i < numpoints - 1; i++) {
            double distance = distance(x[i], y[i], x[i + 1], y[i + 1]);
            if (distance > parameters.getLength1()) {
                return true; // Condition met
            }
        }
        return false;
    }

    public static boolean conditionOne(Parameters parameters, double[] x, double[] y, int numpoints) {
        /*input params Radius1 x,y vector and numpoints 
         * returns true if there exists at least one set of three consecutive data points that cannot be contained in a circle of radius RADIUS1
         * else return false
         */
        if (numpoints < 3) {
            return false;
        }

        // Iterate through all sets of three consecutive points.
        for (int i = 0; i < numpoints - 2; i++) {
            // Get the coordinates of the three consecutive points.
            double x1 = x[i]; double y1 = y[i];
            double x2 = x[i + 1]; double y2 = y[i + 1];
            double x3 = x[i + 2]; double y3 = y[i + 2];

            double d1 = distance(x1, y1, x2, y2);
            double d2 = distance(x2, y2, x3, y3);
            double d3 = distance(x3, y3, x1, y1);

            double dAvragebetween= (d1 + d2 + d3) / 2;
            double area = Math.sqrt(dAvragebetween * (dAvragebetween - d1) * (dAvragebetween - d2) * (dAvragebetween - d3));
            
            if (area == 0) {
                if (d1 <= 2 *parameters.getRadius1() && d2 <= 2 *parameters.getRadius1() && d3 <= 2 *parameters.getRadius1())
                    return true;
            }else{
            double circumRadius = (d1 * d2 * d3) / (4 * area);
                if (circumRadius > parameters.getRadius1()) {
                    return true; // Condition met    
                }
            }
        }
        return false;
    }

    public static boolean conditionTwo(Parameters parameters, double[] x, double[] y, int numpoints) {
        /*input param Epsilon and PI x,y vector and numpoints 
         * returns true if exists at least one set of three consecutive data points which form an angle
         * else return false
         */
        //Not sure how to both get PI and Epsilon from parameters so at moment Math.PI as PI and parameters as Epsilon
        if (numpoints < 3 || parameters.getEpsilon() < 0 || parameters.getEpsilon() >= Main.PI) {
            return false; 
        }
        for (int i = 0; i < numpoints - 2; i++) {
            double x1 = x[i], y1 = y[i];   
            double x2 = x[i + 1], y2 = y[i + 1]; 
            double x3 = x[i + 2], y3 = y[i + 2]; 

            if ((x1 == x2 && y1 == y2) || (x3 == x2 && y3 == y2)) {
                continue;
            }
    
            // Calculate the angle between the vectors
            double vector1X = x1 - x2;
            double vector1Y = y1 - y2;
    
            double vector2X = x3 - x2;
            double vector2Y = y3 - y2;
    
            double dotProduct = (vector1X * vector2X) + (vector1Y * vector2Y);
    
            double magnitude1 = Math.sqrt(vector1X * vector1X + vector1Y * vector1Y);
            double magnitude2 = Math.sqrt(vector2X * vector2X + vector2Y * vector2Y);
    
            double angle = Math.acos(dotProduct / (magnitude1 * magnitude2));
    
            // Check if the angle is outside the acceptable range
            if (angle < (Math.PI - parameters.getEpsilon()) || angle > (Math.PI + parameters.getEpsilon())) {
                return true; // Condition met
            }
        }
        return false;
    }
 

    public static boolean conditionThree(Parameters parameters, double[] x, double[] y, int numpoints) {
        /*input param area1 x,y vector and numpoints 
         * returns true if there are 3 consecutive points that form a triangle with an area greater than AREA1
         * else return false
         */
        if (numpoints < 3 || parameters.getArea1() < 0) {
            return false; 
        }
    
        // Iterate through all sets of three consecutive points
        for (int i = 0; i < numpoints - 2; i++) {
            // Get the coordinates of the three consecutive points
            double x1 = x[i], y1 = y[i];     // First point
            double x2 = x[i + 1], y2 = y[i + 1]; // Second point
            double x3 = x[i + 2], y3 = y[i + 2]; // Third point
    
            // Calculate the area of the triangle
            double area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);
        
            // Check if the area is greater than AREA1
            if (area > parameters.getArea1()) {
                return true; // Condition met
            }
        }
        return false;
        
    }

    public static boolean conditionFour(Parameters parameters, double[] x, double[] y, int numpoints) {
        /*input param Q_PTS, QUADS x,y vector and numpoints 
         * returns true if there exists at least one set of Q_PTS consecutive data points that lie in more than QUADS quadrants
         * else return false
         */


        if (parameters.getQPts() < 2 || parameters.getQPts() > numpoints || parameters.getQuads() < 1 || parameters.getQuads() > 3) {
            return false; // Invalid parameters
        }
        // Iterate over all possible Q_PTS 
        for (int i = 0; i <= numpoints - parameters.getQPts(); i++) {
            Set<Integer> quadrantSet = new HashSet<>();

            // Collect unique quadrants in the subset
            for (int j = i; j < i + parameters.getQPts(); j++) {
                if (x[j] >= 0 && y[j] > 0){ quadrantSet.add(1);}
                else if (x[j] < 0 && y[j] >= 0){quadrantSet.add(2);}// Quadrant II
                else if (x[j]<= 0 && y[j]< 0){quadrantSet.add(3);} // Quadrant III
                else{quadrantSet.add(4);}// Quadrant IV
            }

            if (quadrantSet.size() > parameters.getQuads()) {
                return true; // Condition met
            }
        }
        return false; // Condition not met
        
    }

   
    public static boolean conditionFive(double[] X, int numpoints) {

        if (X.length == numpoints) {

            for (int i = 0; i < numpoints - 1; i++) {
                if (X[i + 1] - X[i] < 0) {
                    return true;
                }
            }
        } else {
            throw new IllegalArgumentException("The length of X should be equal to numpoints");
        }

        return false;
    }

    private boolean conditionSix(int parameters) {
        return false;
    }

    private boolean conditionSeven(int parameters) {
        return false;
    }

    private boolean conditionEight(int parameters) {
        return false;
    }

    private boolean conditionNine(int parameters) {
        return false;
    }

    private boolean conditionTen(int parameters) {
        return false;
    }

    private boolean conditionEleven(int parameters) {
        return false;
    }

    private boolean conditionTwelve(int parameters) {
        return false;
    }

    private boolean conditionThirteen(int parameters) {
        return false;
    }

    private boolean conditionFourteen(int parameters) {
        return false;
    }

}

