package main.model;

public class Imagen
{
	private int _id;
	private String _nombre;
	private String _carpeta;
	private String _estado;
	private String _descripcion;
	private String _extension;
	private String _filename;

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

	public String get_carpeta()
	{
		return _carpeta;
	}

	public void set_carpeta(String _carpeta)
	{
		this._carpeta = _carpeta;
	}

	public String get_estado()
	{
		return _estado;
	}

	public void set_estado(String _estado)
	{
		this._estado = _estado;
	}
	
	public String get_descripcion()
    {
        return _descripcion;
    }

    public void set_descripcion(String _descripcion)
    {
        this._descripcion = _descripcion;
    }

    public String get_extension()
    {
        return _extension;
    }

    public void set_extension(String _extension)
    {
        this._extension = _extension;
    }

    public String get_filename()
    {
        return _filename;
    }

    public void set_filename(String _filename)
    {
        this._filename = _filename;
    }

    public Imagen()
	{	
	}
	
	public Imagen(String nombre, String estado, String carpeta, String descripcion, String extension, String filename)
	{
		this._nombre = nombre;
		this._estado = carpeta;
		this._estado = estado;
		this._descripcion = descripcion;
		this._extension = extension;
		this._filename = filename;
	}

	public Imagen(int id, String nombre, String estado, String carpeta, String descripcion, String extension, String filename)
	{
		this._id = id;
		this._nombre = nombre;
		this._estado = carpeta;	
		this._estado = estado;
		this._descripcion = descripcion;
		this._extension = extension;
        this._filename = filename;
	}
}
