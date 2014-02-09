package com.oscar.garcia.appBQ.entities;

import java.io.InputStream;
import java.util.Date;

import nl.siegmann.epublib.epub.EpubReader;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.RESTUtility;
import com.oscar.garcia.appBQ.assests.Constants;

public class Book implements Comparable {
	private String _tittle;
	private Date _date;
	private String _dropBoxPath;
	private String _internalPath;
	private boolean _downloaded;
	private String _fileName;
	private Bitmap _coverImage;

	public Book(String _tittle, Date _date, String _path, String _fileName) {
		super();
		this._tittle = _tittle;
		this._date = _date;
		this._dropBoxPath = _path;
		this._fileName = _fileName;
		_downloaded = false;
	}

	public Book(Entry entry) {
		// TODO: TIENES QUE PARSEAR UN ENTRY A UN BOOK, IMPORTANTE!
		_date = RESTUtility.parseDate(entry.modified);
		_fileName = entry.fileName();
		_dropBoxPath = entry.path;
	}

	public void parserBook(AssetManager assetManager) {
		try {
			InputStream epubInputStream = assetManager.open(_internalPath + "/"
					+ _fileName);
			nl.siegmann.epublib.domain.Book book = (new EpubReader())
					.readEpub(epubInputStream);
			_tittle = book.getMetadata().getTitles().toString();
			_coverImage = BitmapFactory.decodeStream(book.getCoverImage()
					.getInputStream());
		} catch (Exception e) {
	
		}
	}

	/**
	 * @param _downloaded
	 *            the _downloaded to set
	 */
	public void set_downloaded(boolean _downloaded) {
		this._downloaded = _downloaded;
	}

	/**
	 * @return the _internalPath
	 */
	public String get_internalPath() {
		return _internalPath;
	}

	/**
	 * @return the _coverImage
	 */
	public Bitmap get_coverImage() {
		return _coverImage;
	}

	/**
	 * @return the _fileName
	 */
	public String get_fileName() {
		return _fileName;
	}

	/**
	 * @param _fileName
	 *            the _fileName to set
	 */
	public void set_fileName(String _fileName) {
		this._fileName = _fileName;
	}

	/**
	 * @return the _tittle
	 */
	public String get_tittle() {
		return _tittle;
	}

	/**
	 * @param _tittle
	 *            the _tittle to set
	 */
	public void set_tittle(String _tittle) {
		this._tittle = _tittle;
	}

	/**
	 * @return the _date
	 */
	public Date get_date() {
		return _date;
	}

	/**
	 * @param _date
	 *            the _date to set
	 */
	public void set_date(Date _date) {
		this._date = _date;
	}

	/**
	 * @return the _path
	 */
	public String get_dropBoxPath() {
		return _dropBoxPath;
	}

	/**
	 * @return the _downloaded
	 */
	public boolean is_downloaded() {
		return _downloaded;
	}

	@Override
	public int compareTo(Object another) {
		int aux = 0;
		switch (Constants.getTYPE_COMPARATOR()) {
		case 1:
			aux = _fileName.compareTo(((Book) another).get_fileName());
			break;
		case 2:
			aux = _date.compareTo(((Book) another).get_date());
			break;
		default:
			try {
				Log.e(Constants.TAG_ERROR,
						"La constante de comparaci�n no coincide con ning�n tipo de oradenaci�n.");
				throw new Exception(
						"La constante de comparaci�n no coincide con ning�n tipo de oradenaci�n.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		return aux;
	}
}
