/**
 * 
 */
package graphql.mavenplugin.language;

import java.util.List;

import graphql.mavenplugin.DocumentParser;
import lombok.Data;

/**
 * This class describers one field of one objet type (or interface...). It aims to be simple enough, so that the
 * Velocity template can easily generated ths fields from it.<BR/>
 * For instance:
 * 
 * <PRE>
 * name: String!
 * </PRE>
 * 
 * or
 * 
 * <PRE>
 * appearsIn: [Episode!]!
 * </PRE>
 * 
 * @author EtienneSF
 */
@Data
public class Field {

	/**
	 * The {@link DocumentParser} instance, which will allow to get the {@link Type} of the field, from its typeName,
	 * after the whole parsing is finished
	 */
	DocumentParser documentParser;

	/** The name of the field */
	private String name;

	/**
	 * The type of this field. This type is either the type of the field (if it's not a list), or the type of the items
	 * in the list (if it's a list)
	 */
	private String typeName;

	/** All fields in an object may have parameters. A parameter is actually a field. */
	private List<Field> inputParameters = null;

	/** Is this field a list? */
	private boolean list = false;

	/**
	 * Is this field mandatory? If this field is a list, then mandatory indicates whether the list itself is mandatory,
	 * or may be nullable
	 */
	private boolean mandatory = false;

	/** Indicates whether the item in the list are not nullable, or not. Only used if this field is a list. */
	private boolean itemMandatory = false;

	/** Contains the default value.. Only used if this field is a list. */
	private String defaultValue = null;

	public Field(DocumentParser documentParser) {
		this.documentParser = documentParser;
	}

	/**
	 * Retrieves the {@link Type} for this field
	 * 
	 * @return
	 */
	public Type getType() {
		Type type = documentParser.getType(typeName);
		if (type == null) {
			throw new NullPointerException("Could not find any Type of name '" + typeName + "'");
		}
		return type;
	}

	/**
	 * Convert the given name, which is supposed to be in camel case (for instance: thisIsCamelCase) to a pascal case
	 * string (for instance: ThisIsCamelCase).
	 * 
	 * @return
	 */
	public String getPascalCaseName() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}