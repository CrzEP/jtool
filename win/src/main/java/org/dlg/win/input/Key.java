package org.dlg.win.input;

import lombok.Getter;

/**
 * 按键
 *
 * @author lingui
 */
public enum Key {
    /**
     * 按键
     */
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H'),
    I('I'),
    J('J'),
    K('K'),
    L('L'),
    M('M'),
    N('N'),
    O('O'),
    P('P'),
    Q('Q'),
    R('R'),
    S('S'),
    T('T'),
    U('U'),
    V('V'),
    W('W'),
    X('X'),
    Y('Y'),
    Z('Z'),
    SPACE(' ');

    @Getter
    private final int code;

    Key(int code) {
        this.code = code;
    }

}