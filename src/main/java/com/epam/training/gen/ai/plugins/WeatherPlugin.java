package com.epam.training.gen.ai.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherPlugin {

    @DefineKernelFunction(
            name = "GetCurrentWeather",
            description = "Gets the current weather for a specified city."
    )
    public String getCurrentWeather(
            @KernelFunctionParameter(name = "city", description = "The city to get the weather for.") String city
    ) {
        log.info("Getting current weather for: {}", city);
        // We are not calling Real weather api for now
        if (city.equalsIgnoreCase("Delhi")) {
            return "The current weather in Delhi is: Cloudy, 15°C.";
        } else if (city.equalsIgnoreCase("Mumbai")) {
            return "The current weather in Mumbai is: Sunny, 22°C.";
        }  else {
            return "Weather information not available for " + city + ".";
        }
    }

    @DefineKernelFunction(
            name = "GetWeatherForecast",
            description = "Gets the weather forecast for a specified city."
    )
    public String getWeatherForecast(
            @KernelFunctionParameter(name = "city", description = "The city to get the weather forecast for.") String city,
            @KernelFunctionParameter(name = "days", description = "Number of days for the forecast") String days
    ) {
        log.info("Getting weather forecast for: {} for {} days", city, days);
        // Mocking the forecast response.
        if (city.equalsIgnoreCase("Delhi")) {
            return "The weather forecast for Delhi for the next " + days + " days is: Rain expected.";
        } else if (city.equalsIgnoreCase("Mumbai")) {
            return "The weather forecast for Mumbai for the next " + days + " days is: Sunny.";
        } else {
            return "Weather forecast not available for " + city + ".";
        }
    }
}