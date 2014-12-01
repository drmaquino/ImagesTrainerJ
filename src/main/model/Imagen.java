package main.model;

public class Imagen
{
	private int _id;
	private String _nombre;
	private String _estado;
	private boolean _modified;

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public String get_nombre()
	{
		return _nombre;
	}

	public void set_nombre(String _nombre)
	{
		this._nombre = _nombre;
	}

	public String get_estado()
	{
		return _estado;
	}

	public void set_estado(String _estado)
	{
		this._estado = _estado;
	}
	
	public boolean is_modified()
	{
		return _modified;
	}

	public void set_modified(boolean _modified)
	{
		this._modified = _modified;
	}

	public Imagen()
	{	
		this._modified = false;
	}
	
	public Imagen(String nombre, String estado)
	{
		this._nombre = nombre;
		this._estado = estado;	
		this._modified = false;
	}

	public Imagen(int id, String nombre, String estado)
	{
		this._id = id;
		this._nombre = nombre;
		this._estado = estado;
		this._modified = false;
	}
}
