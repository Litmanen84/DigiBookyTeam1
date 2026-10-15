package com.example.digibookyteam1.lending;

public record ReturnBookResult(
        Lending lending,
        boolean isLate
) {
}
