/*
 * Copyright 2014-2015 Pawel Pastuszak
 *
 * This file is part of VisEditor.
 *
 * VisEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VisEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VisEditor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kotcrab.vis.editor.module.project;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class EditorFont implements Disposable {
		private FileHandle file;
		private FreeTypeFontGenerator generator;
		private ObjectMap<Integer, BitmapFont> bitmapFonts = new ObjectMap<>();

		public EditorFont (FileHandle file) {
			this.file = file;
			generator = new FreeTypeFontGenerator(file);
			get(FontCacheModule.DEFAULT_FONT_SIZE);
		}

		public BitmapFont get (int size) {
			BitmapFont font = bitmapFonts.get(size);

			if (font == null) {
				FreeTypeFontParameter parameter = new FreeTypeFontParameter();
				parameter.size = size;
				parameter.minFilter = TextureFilter.Linear;
				parameter.magFilter = TextureFilter.Linear;
				font = generator.generateFont(parameter);
				bitmapFonts.put(size, font);
			}

			return font;
		}

		@Override
		public void dispose () {
			for (BitmapFont font : bitmapFonts.values())
				font.dispose();

			generator.dispose();
		}

	public FileHandle getFile () {
		return file;
	}
}
