#include <stdio.h>

int main()
{
#ifdef GOOD
    printf("Good");
#else
    printf("Bad");
#endif
}