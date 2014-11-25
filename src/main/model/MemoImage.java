package main.model;

public class MemoImage
{
	private int _id;
	private String _par;
	private String _name;
	private String _src;
	private String _state;

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public String get_par()
	{
		return _par;
	}

	public void set_par(String _par)
	{
		this._par = _par;
	}

	public String get_name()
	{
		return _name;
	}

	public void set_name(String _name)
	{
		this._name = _name;
	}

	public String get_src()
	{
		return _src;
	}

	public void set_src(String _src)
	{
		this._src = _src;
	}

	public String get_state()
	{
		return _state;
	}

	public void set_state(String _state)
	{
		this._state = _state;
	}
}
