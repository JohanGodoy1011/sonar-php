<?php
declare(ticks=1);                              // OK

function f() {
  doSomething();                               // OK
  doSomethingElse();;                          // NOK {{Remove this empty statement.}}
  ;                                            // NOK
}

for ($i = 1; $i <= 10; doSomething(), $i++);   // NOK


