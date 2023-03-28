float *(*pArray)[2][3];
int f(int x, float *y);
int (*f)(int x, float *);
void *f(int xArray[3], float *, char c);
int f[2](int x, char c);
int (*f[2])(int x, float *);
int *(*f)[2](int x);
int *(**f)[2][3](int x, float *);
void **(*f)[2](int *x, float *(**pArray)[2][3], char *);