package edu.birzeit.swms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolygonDto {

    List<PointDto> pointDtoList = new ArrayList<>();

//    public PolygonDto(Polygon polygon) {
//        int count = polygon.npoints;
//        int x, y;
//        for (int i = 0; i < count; ++i) {
//            x = polygon.xpoints[i];
//            y = polygon.ypoints[i];
//            this.pointDtoList.add(new PointDto(x, y));
//        }
//    }
//
//    public Polygon getPolygon() {
    //        Polygon polygon = new Polygon();
    //        int count = this.getPointDtoList().size();
    //        for (int i = 0; i < count; ++i) {
    //            int x = (int) this.getPointDtoList().get(i).getX();
    //            int y = (int) this.getPointDtoList().get(i).getY();
    //            polygon.addPoint(x, y);
    //        }
    //        return polygon;
//    }


}
