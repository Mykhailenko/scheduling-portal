/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.rm.client.monitoring.charts;

import java.util.Date;

import org.ow2.proactive_grid_cloud_portal.rm.client.RMController;
import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


/**
 * Shows the memory information on host.
 */
public class MemoryLineChart extends MBeanTimeAreaChart {

    public MemoryLineChart(RMController controller, String jmxServerUrl) {
        super(controller,
              jmxServerUrl,
              "sigar:Type=Mem",
              new String[] { "ActualUsed", "ActualFree", "Total" },
              "Memory");

        setYAxesTicksSuffix(" Mb");

        setColors("#fcaf3e", "#35a849", "#3a668d");
        loadOpts.setLegend(LegendPosition.RIGHT);
        setNames("Used", "Free", "Total");
    }

    @Override
    public double formatValue(double value) {
        return (long) (value / (1024 * 1024));
    }

    @Override
    public void processResult(String result) {
        JSONArray array = controller.parseJSON(result).isArray();
        if (array != null) {
            String timeStamp = DateTimeFormat.getFormat(PredefinedFormat.HOUR24_MINUTE)
                                             .format(new Date(System.currentTimeMillis()));

            addLabel(timeStamp);

            for (int i = 0; i < attrs.length; i++) {
                double value = array.get(i).isObject().get("value").isNumber().doubleValue();
                addPointToDataset(i, formatValue(value));
            }

            loadChart.update();
        }
    }

    @Override
    public AbstractChart createChart(DataTable data, Options opts) {
        return new LineChart();
    }
}
