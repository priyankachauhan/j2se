#include "stdio.h"
#include <unistd.h>

void sayhello()
{
    //printf("sleep 30s\n");
    //sleep(30); // sleep is only for linux
    printf("hello world\n");
}

int add(int a, int b)
{
    return a + b;
}
