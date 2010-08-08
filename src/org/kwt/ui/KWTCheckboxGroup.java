/*
 * Copyright 2010 Dan Fabulich.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the LICENSE file included with this
 * distribution.
 * 
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the
 * License.
 */

package org.kwt.ui;

/** 
 * This group binds together multiple radio buttons, ensuring that only one
 * radio button is selected at a time.
 * 
 * @author Dan Fabulich
 *
 */
public class KWTCheckboxGroup {
    private KWTCheckbox selected = null;

    /** 
     * Returns the currently selected radio button.
     * 
     * @return the currently selected radio button
     */
    public KWTCheckbox getSelected() {
        return selected;
    }

    /** 
     * Selects a radio button, deselecting the previously selected button.
     * 
     * @param box a radio button belonging to this group, or <code>null</code> to deselect
     * everything.
     */
    public void setSelected(KWTCheckbox box) {
        if (box != null && box.group != this) {
            return;
        }
        KWTCheckbox oldChoice = this.selected;
        this.selected = box;
        if (oldChoice != null && oldChoice != box && oldChoice.group == this) {
            oldChoice.setSelected(false);
        }
        if (box != null && oldChoice != box && !box.isSelected()) {
            box.setSelected(true);
        }
    }

}
