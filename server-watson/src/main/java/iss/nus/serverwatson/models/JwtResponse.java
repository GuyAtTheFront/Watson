package iss.nus.serverwatson.models;

public record JwtResponse(String username, String userToken, String expiresAt) {}
