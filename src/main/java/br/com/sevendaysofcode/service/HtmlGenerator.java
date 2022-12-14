package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.Content;

import java.io.PrintWriter;
import java.util.List;

public class HtmlGenerator {

    private final PrintWriter writer;

    public HtmlGenerator(PrintWriter writer) {
        this.writer = writer;
    }

    public void generate(List<Content> content) {
        writer.println(
                """
                        <html>
                            <head>
                                <meta charset=\"utf-8\">
                                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">
                                <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" 
                                            + "integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">
                                 <style>
                                    .grid{
                                         display: grid;
                                         grid: auto-flow / 100% 50%;
                                         grid-column-end: span 2;
                                         grid-row-end: span 2;
                                         grid-template-columns: repeat(5,1fr);
                                         grid-gap: 5px;
                                     }
                                 </style>           
                            </head>
                            <body class=\"grid\">
                        """);

        for (Content c : content) {
            String div =
                    """
                    <div class=\"card text-white bg-dark mb-3\" style=\"max-width: 18rem;\">
                        <h4 class=\"card-header\">%s</h4>
                        <div class=\"card-body\">
                            <img class=\"card-img\" src=\"%s\" alt=\"%s\">
                            <p class=\"card-text mt-2\">Nota: %s - Ano: %s</p>
                        </div>
                    </div>
                    """;

            writer.println(String.format(div, c.title(), c.urlImage(), c.title(), c.rating(), c.year()));
        }


        writer.println(
                """
                    </body>
                </html>
                """);
    }

}
