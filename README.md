# C-Language-Parser  
 a programming challenge for a PhD offer in University at Buffalo  

## Require  

1. a simple parser for recognizing the type of a C-language declaration  
   1. parse common use cases  
      1. C99 standard here: [ISO/IEC 9899:1999 (open-std.org)](https://www.open-std.org/jtc1/sc22/wg14/www/docs/n1256.pdf)  
      2. clause 6.2.5 on page 33 and clause 6.7 on page 97  
   2. any storage class specifiers (extern, static or volatile)  
      1. do not consider  
   3. special rules  
      1. the C99 standard draft, clause 6.7.5.3, # 7 and # 8 for details.  
   4. implicit return type  
      1. defaults to int  
      2. do not have to consider this case  
   5. variadic function parameters in function declarations  
      1. do not have to consider  
2. [ANTLR](https://www.antlr.org/) in Java  
   1. a parser generator  
   2. a tutorial and a description of  the LL grammar  
3. 10 days (by 4/1/2023)  
4. source code  
5.  the report  
   1. spell out your understanding of the problem  
   2. your approaches  

## A few sample inputs and outputs  

Input 1:  

```C  
int x;  
```

Output 1:  

```C  
int  
```

  

Input 2:  

```C  
int x[2];  
```

Output 2:  

```C  
size-2 array of int  
```

  

Input 3:  

```C  
int *x[2];  
```

Output 3:  

```C  
size-2 array of pointer to int  
```

  

Input 4:  

```C  
int f(int x, float *y);  
```

Output 4:  

```C  
function that accepts (int and pointer to float) returning int  
```

  

Input 5:  

```C  
int (*f)(int x, float *);  
```

Output 5:  

```C  
pointer to function that accepts (int and pointer to float) returning int  
```

  

Input 6:  

```C  
int (*f[2])(int x, float *);  
```

Output 6:  

```C  
size-2 array of pointer to (function that accepts (int and pointer to float) returning int)  
```
