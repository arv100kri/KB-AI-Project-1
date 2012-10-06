package org.miller.definitions;
/*
 * @author Arvind Krishnaa Jagannathan
 */
/*
 * A class to represent a relation between two shapes
 * It is an ordered pair <shape1><shape2><relation>
 */
public class Relation {
	private Shape Shape1, Shape2;
	private String relationship;
	
        /*
         * Returns the first shape in the relation
         * @return First shape
         */
	public Shape getShape1() {
		return Shape1;
	}
	
        /*
         * Set the value of the first shape
         * @param shape1 The value of the first shape
         */
        public void setShape1(Shape shape1) {
		Shape1 = shape1;
	}
	
        /*
         * Returns the second shape in the relation
         * @return Second shape
         */
        public Shape getShape2() {
		return Shape2;
	}
	
        /*
         * Set the value of the second shape
         * @param shape2 The value of the second shape
         */
        
        public void setShape2(Shape shape2) {
		Shape2 = shape2;
	}
        
        /*
         * Returns the value of the relation between the shapes
         * @return The relationship between shape1 and shape2
        */
	public String getRelationship() {
		return relationship;
	}
	
        /*
         * Set the value of the relation between the shapes
         * @param relationship The string representation of their relation. Can be "leftOf", "rightOf" and so on
         */
        
        public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
        
	@Override
	public String toString() {
		return Shape1 + " '" + relationship + "' " + Shape2;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Shape1 == null) ? 0 : Shape1.hashCode());
		result = prime * result + ((Shape2 == null) ? 0 : Shape2.hashCode());
		result = prime * result
				+ ((relationship == null) ? 0 : relationship.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (Shape1 == null) {
			if (other.Shape1 != null)
				return false;
		} else if (!Shape1.equals(other.Shape1))
			return false;
		if (Shape2 == null) {
			if (other.Shape2 != null)
				return false;
		} else if (!Shape2.equals(other.Shape2))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		return true;
	}
}