package com.fidosoft.por2tok.ui;
import java.text.*;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter.Change;

public class NumericFormatter implements UnaryOperator<Change> {
  private static final DecimalFormat format = new DecimalFormat( "#.0" );

  @Override
  public Change apply(Change c) {
    if (c.getControlNewText().isEmpty()) {
      return c;
    }

    ParsePosition parsePosition = new ParsePosition(0);
    Object object = format.parse(c.getControlNewText(), parsePosition);

    if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
      return null;
    } else {
      return c;
    }
  }
}
