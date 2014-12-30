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
        extractFilenameFromAtributes();
        return _filename;
    }

    public void set_filename(String _filename)
    {
        this._filename = _filename;
        extractNombreFromFilename();
        extractDescripcionFromFilename();
        extractExtensionFromFilename();
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

    private void extractNombreFromFilename()
    {
        if (this._filename.contains("(") && this._filename.contains(")"))
        {
            int start = this._filename.indexOf("(");
            set_nombre(this._filename.substring(0, start - 1));
        }
        else
        {
            int stop = this._filename.indexOf(".");
            set_nombre(this._filename.substring(0, stop));
        }
    }

    private void extractDescripcionFromFilename()
    {
        if (this._filename.contains("(") && this._filename.contains(")"))
        {
            int start = this._filename.indexOf("(");
            int end = this._filename.indexOf(")");
            set_descripcion(this._filename.substring(start + 1, end));
        }
        else
        {
            set_descripcion("");
        }
    }

    private void extractExtensionFromFilename()
    {
        int stop = this._filename.indexOf(".");
        set_extension(this._filename.substring(stop + 1, this._filename.length()));
    }

    private void extractFilenameFromAtributes()
    {
        if (_nombre != null && _extension != null)
        {
            if (!_descripcion.equals(""))
            {
                _filename = String.format("%s (%s).%s", _nombre, _descripcion, _extension);
            }
            else
            {
                _filename = String.format("%s.%s", _nombre, _extension);
            }
        }
    }
}
