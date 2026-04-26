package net.justonedev.graphs.reingold_tilford;

import java.util.LinkedList;
import java.util.List;

public class Contours {
    private final List<Integer> leftContour;
    private List<Integer> rightContour;

    public Contours() {
        leftContour = new LinkedList<>();
        rightContour = new LinkedList<>();
        leftContour.add(0);
        rightContour.add(0);
    }

    public int getShift() {
        return rightContour.size() > 1 ? rightContour.get(1) : rightContour.getFirst();
    }

    // merges right contour. modifies contour lists, and reassigns the right contour.
    public void mergeRight(Contours rightContours) {
        // This requires a custom LinkedList implementation to access sublist nodes for efficient emplacing
        int shift = calculateSymmetricalShift(rightContour, rightContours.leftContour);

        if (leftContour.size() == rightContours.rightContour.size()) {
            leftContour.add(1, -shift);
            rightContours.rightContour.add(1, shift);
            rightContour = rightContours.rightContour;
            return;
        }

        // Note: Of a single Contours object, left and right will always have the same length.
        // The constructor and this method ensure this, and are the only methods that modify
        // those lengths.

        int sharedLength = Math.min(leftContour.size(), rightContours.rightContour.size());
        int difference = 0;
        for (int i = 0; i < sharedLength; i++) {
            difference = rightContour.get(i) - rightContours.leftContour.get(i);
        }

        if (leftContour.size() > rightContours.leftContour.size()) {
            // this subtree is bigger than that of the other contours.
            // thus, we need to adjust the right contour. left can stay as-is.
            // todo += difference?
            var newRightContour = rightContours.rightContour;
            newRightContour.add(rightContour.get(sharedLength) + difference);
            // Copy rest of the contour
            for (int i = sharedLength + 1; i < rightContour.size(); i++) {
                newRightContour.add(rightContour.get(i));
            }
            this.rightContour = newRightContour;
        } else {
            // right subtree is bigger, left contour needs adjusting.
            // todo -= difference?
            leftContour.add(rightContours.leftContour.get(sharedLength) - difference);
            // Copy rest of the contour
            for (int i = sharedLength + 1; i < rightContours.leftContour.size(); i++) {
                leftContour.add(rightContours.leftContour.get(i));
            }
        }
        leftContour.add(1, -shift);
        rightContour.add(1, shift);
    }

    private static int calculateSymmetricalShift(List<Integer> leftInnerContour, List<Integer> rightInnerContour) {
        int leftSum = 0;
        int rightSum = 0;
        //int sum = 0;
        int maximum = 0;

        for (int i = 0; i < Math.min(leftInnerContour.size(), rightInnerContour.size()); i++) {
            leftSum += leftInnerContour.get(i);
            rightSum += rightInnerContour.get(i);
            maximum = Math.max(maximum, leftSum - rightSum);
            // this can be better by directly working on a single working sum:
            //sum += leftInnerContour.get(i) - rightInnerContour.get(i);
            //maximum = Math.max(maximum, sum);
        }

        return (int) Math.ceil((maximum + 1) / 2d);
    }

}
