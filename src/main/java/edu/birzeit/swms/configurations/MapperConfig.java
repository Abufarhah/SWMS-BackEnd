package edu.birzeit.swms.configurations;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.PointDto;
import edu.birzeit.swms.dtos.PolygonDto;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import lombok.extern.java.Log;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Log
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {

        Converter<Polygon, PolygonDto> toAreaDto = new
                AbstractConverter<Polygon, PolygonDto>() {
                    protected PolygonDto convert(Polygon source) {
                        PolygonDto polygonDto = new PolygonDto();
                        int count = source.npoints;
                        double x, y;
                        for (int i = 0; i < count; ++i) {
                            x = source.xpoints[i] / Math.pow(10, 7);
                            y = source.ypoints[i] / Math.pow(10, 7);
                            polygonDto.getPointDtoList().add(new PointDto(x, y));
                        }
                        return polygonDto;
                    }
                };

        Converter<PolygonDto, Polygon> fromAreaDto = new
                AbstractConverter<PolygonDto, Polygon>() {
                    protected Polygon convert(PolygonDto source) {
                        Polygon polygon = new Polygon();
                        int x, y;
                        int count = source.getPointDtoList().size();
                        for (int i = 0; i < count; ++i) {
                            x = (int) (source.getPointDtoList().get(i).getX() * Math.pow(10, 7));
                            y = (int) (source.getPointDtoList().get(i).getY() * Math.pow(10, 7));
                            polygon.addPoint(x, y);
                        }
                        return polygon;
                    }
                };


        PropertyMap<Area, AreaDto> areaToDTOMap = new PropertyMap<Area, AreaDto>() {
            protected void configure() {
                using(toAreaDto).map(source.getPolygon()).setPolygonDto(new PolygonDto());
            }
        };


        PropertyMap<AreaDto, Area> dtoToAreaMap = new PropertyMap<AreaDto, Area>() {
            protected void configure() {
                using(fromAreaDto).map(source.getPolygonDto()).setPolygon(new Polygon());
            }
        };

        Converter<Point, PointDto> toPointDto = new
                AbstractConverter<Point, PointDto>() {
                    protected PointDto convert(Point source) {
                        PointDto pointDto = new PointDto();
                        pointDto.setX(source.getX() / Math.pow(10, 7));
                        pointDto.setY(source.getY() / Math.pow(10, 7));
                        return pointDto;
                    }
                };

        Converter<PointDto, Point> fromPointDto = new
                AbstractConverter<PointDto, Point>() {
                    protected Point convert(PointDto source) {
                        Point point = new Point();
                        point.setLocation(source.getX() * Math.pow(10, 7),
                                source.getY() * Math.pow(10, 7));
                        return point;
                    }
                };


        PropertyMap<Bin, BinDto> pointToDTOMap = new PropertyMap<Bin, BinDto>() {
            protected void configure() {
                using(toPointDto).map(source.getLocation()).setLocation(new PointDto());
            }
        };


        PropertyMap<BinDto, Bin> dtoToPointMap = new PropertyMap<BinDto, Bin>() {
            protected void configure() {
                using(fromPointDto).map(source.getLocation()).setLocation(new Point());
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(dtoToAreaMap);
        modelMapper.addMappings(areaToDTOMap);
        modelMapper.addMappings(dtoToPointMap);
        modelMapper.addMappings(pointToDTOMap);
        return modelMapper;
    }

}
