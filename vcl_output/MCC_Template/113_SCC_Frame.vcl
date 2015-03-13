int ?@SCC113V0?(int nb) {                    // package-private
        if (limit - position < nb)
            throw new ?@SCC113V1?();
        int p = position;
        position += nb;
        return p;
    }#break break_113