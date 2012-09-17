package org.miller.definitions;
/*
 * @author Arvind Krishnaa Jagannathan
 */

/*
 * Combines the attributes of a shape into a class
 */
public class Shape 
{
	private String type;
	private int Id;
	
        /*
         * Returns the shape type
         * @return The shape type
         */
	public String getType() {
		return type;
	}
	
        /*
         * Sets the type of the shape
         * @param type The shape's type
         */
        public void setType(String type) {
		this.type = type;
	}

        /*
         * Returns the shape identifier
         * @return The shape identifier
         */
        public int getId() {
		return Id;
	}
        
	/*
         * Sets the identifier of the shape
         * @param type The shape's identifier
         */
        public void setId(int id) {
		Id = id;
	}
        
	@Override
	public String toString() {
		return type + Id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Shape other = (Shape) obj;
		if(other.getType().equalsIgnoreCase("Frame"))		//Assuming a frame is any shape
			return true;
		if (Id != other.Id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}