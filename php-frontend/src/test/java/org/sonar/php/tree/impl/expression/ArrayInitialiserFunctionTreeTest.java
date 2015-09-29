/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010 SonarSource and Akram Ben Aissi
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.php.tree.impl.expression;

import org.junit.Test;
import org.sonar.php.PHPTreeModelTest;
import org.sonar.php.parser.PHPLexicalGrammar;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.ArrayInitialiserFunctionTree;
import org.sonar.plugins.php.api.tree.expression.ArrayPairTree;

import static org.fest.assertions.Assertions.assertThat;

public class ArrayInitialiserFunctionTreeTest extends PHPTreeModelTest {

  @Test
  public void empty() throws Exception {
    ArrayInitialiserFunctionTree tree = parse("array()", PHPLexicalGrammar.COMBINED_SCALAR);

    assertThat(tree.is(Kind.ARRAY_INITIALISER_FUNCTION)).isTrue();

    assertThat(tree.arrayToken().text()).isEqualTo("array");
    assertThat(tree.openParenthesisToken().text()).isEqualTo("(");
    assertThat(tree.arrayPairs()).isEmpty();
    assertThat(tree.closeParenthesisToken().text()).isEqualTo(")");
  }

  @Test
  public void non_empty() throws Exception {
    ArrayInitialiserFunctionTree tree = parse("array($a, $b, $c)", PHPLexicalGrammar.COMBINED_SCALAR);

    assertThat(tree.is(Kind.ARRAY_INITIALISER_FUNCTION)).isTrue();

    assertThat(tree.arrayToken().text()).isEqualTo("array");
    assertThat(tree.openParenthesisToken().text()).isEqualTo("(");

    assertThat(tree.arrayPairs()).hasSize(3);
    assertThat(tree.arrayPairs().getSeparators()).hasSize(2);
    assertThat(expressionToString(tree.arrayPairs().get(0))).isEqualTo("$a");

    assertThat(tree.closeParenthesisToken().text()).isEqualTo(")");
  }

  @Test
  public void with_trailing_comma() throws Exception {
    ArrayInitialiserFunctionTree tree = parse("array($a,)", PHPLexicalGrammar.COMBINED_SCALAR);

    assertThat(tree.is(Kind.ARRAY_INITIALISER_FUNCTION)).isTrue();

    assertThat(tree.arrayToken().text()).isEqualTo("array");
    assertThat(tree.openParenthesisToken().text()).isEqualTo("(");

    assertThat(tree.arrayPairs()).hasSize(1);
    assertThat(tree.arrayPairs().getSeparators()).hasSize(1);
    assertThat(expressionToString(tree.arrayPairs().get(0))).isEqualTo("$a");

    assertThat(tree.closeParenthesisToken().text()).isEqualTo(")");
  }

}
