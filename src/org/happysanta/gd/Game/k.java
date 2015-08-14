package org.happysanta.gd.Game;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 


import org.happysanta.gd.Menu.SimpleMenuElement;

public class k {

	public boolean m_doZ;
	public int m_aI;
	public int m_intI;
	public int m_forI;
	public int m_newI;
	public SimpleMenuElement m_ifan[];

	public k() {
		m_ifan = new SimpleMenuElement[6];
		for (int i = 0; i < 6; i++)
			m_ifan[i] = new SimpleMenuElement();

		_avV();
	}

	public void _avV() {
		m_aI = m_forI = m_newI = 0;
		m_doZ = true;
		for (int i = 0; i < 6; i++)
			m_ifan[i].init();

	}
}
